#!/usr/bin/env python
#
# Copyright (c) 2006-2008 Ricardo Garcia Gonzalez
#
# Permission is hereby granted, free of charge, to any person obtaining a
# copy of this software and associated documentation files (the "Software"),
# to deal in the Software without restriction, including without limitation
# the rights to use, copy, modify, merge, publish, distribute, sublicense,
# and/or sell copies of the Software, and to permit persons to whom the
# Software is furnished to do so, subject to the following conditions:
# 
# The above copyright notice and this permission notice shall be included
# in all copies or substantial portions of the Software.
# 
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
# FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
# THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR
# OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
# ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
# OTHER DEALINGS IN THE SOFTWARE.
# 
# Except as contained in this notice, the name(s) of the above copyright
# holders shall not be used in advertising or otherwise to promote the
# sale, use or other dealings in this Software without prior written
# authorization.
#
import httplib
import math
import optparse
import re
import socket
import sys
import time
import urllib2

# Global constants
const_1k = 1024
const_initial_block_size = 10 * const_1k
const_max_block_size = 4 * const_1k * const_1k
const_epsilon = 0.0001
const_timeout = 120

const_video_url_re = re.compile(r'(?:http://)?(?:www\.)?metacafe\.com/watch/([^/]+)/([^/]+/)?.*')
const_normalized_url_str = 'http://www.metacafe.com/watch/%s/'
const_disclaimer_url = 'http://www.metacafe.com/disclaimer'
const_age_post_data = r'allowAdultContent=1&submit=Continue+-+I%27m+over+18'
const_video_mediaurl_re = re.compile(r'"mediaURL":"(http.*?\.flv)"', re.M)
const_video_gdakey_re = re.compile(r'"gdaKey":"(.*?)"', re.M)

# Print error message, followed by standard advice information, and then exit
def error_advice_exit(error_text):
	sys.stderr.write('Error: %s.\n' % error_text)
	sys.stderr.write('Try again several times. It may be a temporary problem.\n')
	sys.stderr.write('Other typical problems:\n\n')
	sys.stderr.write('* URL is wrong.\n')
	sys.stderr.write('* Content is not a flash video.\n')
	sys.stderr.write('* Video no longer exists.\n')
	sys.stderr.write('* The connection was cut suddenly for some reason.\n')
	sys.stderr.write('* metacafe changed their system, and the program no longer works.\n')
	sys.stderr.write('\nTry to confirm you are able to view the video using a web browser.\n')
	sys.stderr.write('When using a proxy, make sure http_proxy has http://host:port format.\n')
	sys.stderr.write('Try again several times and contact me if the problem persists.\n')
	sys.exit('\n')

# Wrapper to create custom requests with typical headers
def request_create(url, extra_headers, post_data):
	retval = urllib2.Request(url)
	if post_data is not None:
		retval.add_data(post_data)
	# Try to mimic Firefox, at least a little bit
	retval.add_header('User-Agent', 'Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.8.1.14) Gecko/20080404 Firefox/2.0.0.14')
	retval.add_header('Accept-Charset', 'ISO-8859-1,utf-8;q=0.7,*;q=0.7')
	retval.add_header('Accept', 'text/xml,application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5')
	retval.add_header('Accept-Language', 'en-us,en;q=0.5')
	if extra_headers is not None:
		for header in extra_headers:
			retval.add_header(header[0], header[1])
	return retval

# Perform a request, process headers and return response
def perform_request(url, headers=None, data=None):
	request = request_create(url, headers, data)
	response = urllib2.urlopen(request)
	return response

# Conditional print
def cond_print(str):
	global cmdl_opts
	if not (cmdl_opts.quiet or cmdl_opts.get_url):
		sys.stdout.write(str)
		sys.stdout.flush()

# Generic download step
def download_step(return_data_flag, step_title, step_error, url, post_data=None):
	try:
		cond_print('%s... ' % step_title)
		data = perform_request(url, data=post_data).read()
		cond_print('done.\n')
		if return_data_flag:
			return data
		return None

	except (urllib2.URLError, ValueError, httplib.HTTPException, TypeError, socket.error):
		cond_print('failed.\n')
		error_advice_exit(step_error)

	except KeyboardInterrupt:
		sys.exit('\n')

# Generic extract step
def extract_step(step_title, step_error, regexp, data):
	try:
		cond_print('%s... ' % step_title)
		match = regexp.search(data)
		
		if match is None:
			cond_print('failed.\n')
			error_advice_exit(step_error)
		
		extracted_data = match.group(1)
		cond_print('done.\n')
		return extracted_data
	
	except KeyboardInterrupt:
		sys.exit('\n')

# Calculate new block size based on previous block size
def new_block_size(before, after, bytes):
	new_min = max(bytes / 2.0, 1.0)
	new_max = max(bytes * 2.0, 1.0)
	dif = after - before
	if dif < const_epsilon:
		return int(new_max)
	rate = bytes / dif / 2.0
	if rate > new_max:
		return int(new_max)
	if rate < new_min:
		return int(new_min)
	return min(int(rate), const_max_block_size)

# Get optimum 1k exponent to represent a number of bytes
def optimum_k_exp(num_bytes):
	global const_1k
	if num_bytes == 0:
		return 0
	return long(math.log(num_bytes, const_1k))

# Get optimum representation of number of bytes
def format_bytes(num_bytes):
	global const_1k
	try:
		exp = optimum_k_exp(num_bytes)
		suffix = 'bkMGTPEZY'[exp]
		if exp == 0:
			return '%s%s' % (long(round(num_bytes)), suffix)
		converted = float(num_bytes) / float(const_1k**exp)
		return '%.2f%s' % (converted, suffix)
	except IndexError:
		sys.exit('Error: internal error formatting number of bytes.')

# Returns a long integer from a string representing a byte size
def parse_bytes(bytestr):
	regexp = re.compile(r'(?i)^(\d+(?:\.\d+)?)([kMGTPEZY]?)$')
	matchobj = regexp.match(bytestr)
	if matchobj is None:
		return None
	number = float(matchobj.group(1))
	multiplier = 1024.0 ** 'bkmgtpezy'.index(matchobj.group(2).lower())
	return long(round(number * multiplier))

# Calculate ETA and return it in string format as MM:SS
def calc_eta(start, now, total, current):
	dif = now - start
	if current == 0 or dif < const_epsilon:
		return '--:--'
	rate = float(current) / dif
	eta = long((total - current) / rate)
	(eta_mins, eta_secs) = divmod(eta, 60)
	if eta_mins > 99:
		return '--:--'
	return '%02d:%02d' % (eta_mins, eta_secs)

# Calculate speed and return it in bytes per second, or None
def calc_speed(start, now, bytes):
	dif = now - start
	if bytes == 0 or dif < const_epsilon:
		return None
	return (float(bytes) / dif)

# Convert speed to string
def speed_to_str(bytespersec):
	if bytespersec is None:
		return 'N/A b'
	return format_bytes(bytespersec)


# Create the command line options parser and parse command line
cmdl_usage = 'usage: %prog [options] video_url'
cmdl_version = '2008.07.23'
cmdl_parser = optparse.OptionParser(usage=cmdl_usage, version=cmdl_version, conflict_handler='resolve')
cmdl_parser.add_option('-h', '--help', action='help', help='print this help text and exit')
cmdl_parser.add_option('-v', '--version', action='version', help='print program version and exit')
cmdl_parser.add_option('-o', '--output', dest='outfile', metavar='FILE', help='output video file name')
cmdl_parser.add_option('-q', '--quiet', action='store_true', dest='quiet', help='activates quiet mode')
cmdl_parser.add_option('-s', '--simulate', action='store_true', dest='simulate', help='do not download video')
cmdl_parser.add_option('-t', '--title', action='store_true', dest='use_title', help='use title in file name')
cmdl_parser.add_option('-g', '--get-url', action='store_true', dest='get_url', help='print final video URL only')
cmdl_parser.add_option('-r', '--rate-limit', action='store', dest='rate_limit', help='download rate limit (e.g. 50k or 44.6m)')
(cmdl_opts, cmdl_args) = cmdl_parser.parse_args()

# Set socket timeout
socket.setdefaulttimeout(const_timeout)

# Get video URL
if len(cmdl_args) != 1:
	cmdl_parser.print_help()
	sys.exit('\n')
video_url = cmdl_args[0]

# Verify video URL format and extract URL data to normalize URL
video_url_mo = const_video_url_re.match(video_url)
if video_url_mo is None:
	sys.exit('Error: URL does not seem to be a metacafe video URL. If it is, report a bug.')
video_url_id = video_url_mo.group(1)
video_url_title = (video_url_mo.group(2) is not None) and video_url_mo.group(2)[:-1] or None
video_url = const_normalized_url_str % video_url_id

# Check conflicting options
if cmdl_opts.outfile is not None and (cmdl_opts.simulate or cmdl_opts.get_url):
	sys.stderr.write('Warning: video file name given but will not be used.\n')

if cmdl_opts.outfile is not None and cmdl_opts.use_title:
	sys.exit('Error: using the video title conflicts with using a given file name.')

if cmdl_opts.quiet and cmdl_opts.get_url:
	sys.exit('Error: cannot be quiet and print final URL at the same time.')

# Incorrect option formatting
rate_limit = None
if cmdl_opts.rate_limit is not None:
	rate_limit = parse_bytes(cmdl_opts.rate_limit)
	if rate_limit is None:
		sys.exit('ERROR: invalid download rate limit specified.')

# Get output file name 
if cmdl_opts.outfile is None:
	if cmdl_opts.use_title:
		if video_url_title is None:
			sys.exit('Error: cannot use video title: not present in URL.')
		video_filename = '%s-%s.flv' % (video_url_title, video_url_id)
	else:
		video_filename = '%s.flv' % video_url_id
else:
	video_filename = cmdl_opts.outfile

# Check name
if not video_filename.lower().endswith('.flv'):
	sys.stderr.write('Warning: video file name does not end in .flv\n')

# Test writable file
if not (cmdl_opts.simulate or cmdl_opts.get_url):
	try:
		disk_test = open(video_filename, 'wb')
		disk_test.close()

	except (OSError, IOError):
		sys.exit('Error: unable to open %s for writing.' % video_filename)

# Install cookie and proxy handlers
urllib2.install_opener(urllib2.build_opener(urllib2.ProxyHandler()))
urllib2.install_opener(urllib2.build_opener(urllib2.HTTPCookieProcessor()))

# Retrieve video webpage
download_step(False, 'Retrieving disclaimer', 'unable to retrieve disclaimer', const_disclaimer_url)

# Retrieve video webpage
video_webpage = download_step(True, 'Retrieving video webpage', 'unable to retrieve video webpage', video_url, const_age_post_data)

# Retrieve real video URL
video_url_real = extract_step('Extracting real video URL', 'unable to extract real video URL', const_video_mediaurl_re, video_webpage)
video_url_real = video_url_real.replace('\\', '')

# Retrieve gdaKey parameter
video_gdakey = extract_step('Extracting "gdaKey" parameter', 'unable to extract "gdaKey" parameter', const_video_gdakey_re, video_webpage)
video_url_real = '%s?__gda__=%s' % (video_url_real, video_gdakey)

# Retrieve video data
try:
	cond_print('Requesting video file... ')
	video_data = perform_request(video_url_real)
	cond_print('done.\n')
	cond_print('Video data found at %s\n' % video_data.geturl())

	if cmdl_opts.get_url:
		print video_data.geturl()

	if cmdl_opts.simulate or cmdl_opts.get_url:
		sys.exit()

	video_file = open(video_filename, 'wb')
	try:
		video_len = long(video_data.info()['Content-length'])
		video_len_str = format_bytes(video_len)
	except KeyError:
		video_len = None
		video_len_str = 'N/A'

	byte_counter = 0
	block_size = const_initial_block_size
	start_time = time.time()
	while True:
		if video_len is not None:
			percent = float(byte_counter) / float(video_len) * 100.0
			percent_str = '%.1f' % percent
			eta_str = calc_eta(start_time, time.time(), video_len, byte_counter)
		else:
			percent_str = '---.-'
			eta_str = '--:--'
		counter = format_bytes(byte_counter)
		speed = calc_speed(start_time, time.time(), byte_counter)
		speed_str = speed_to_str(speed)
		cond_print('\rRetrieving video data: %5s%% (%8s of %s) at %8s/s ETA %s ' % (percent_str, counter, video_len_str, speed_str, eta_str))

		before = time.time()
		video_block = video_data.read(block_size)
		after = time.time()
		dl_bytes = len(video_block)
		if dl_bytes == 0:
			break
		byte_counter += dl_bytes
		video_file.write(video_block)
		block_size = new_block_size(before, after, dl_bytes)

		if rate_limit is not None:
			if block_size > rate_limit:
				block_size = rate_limit / 2

			speed = calc_speed(start_time, time.time(), byte_counter)
			if speed is not None and speed > const_epsilon and speed > rate_limit:
				time.sleep((byte_counter - rate_limit * (time.time() - start_time)) / rate_limit)

	if video_len is not None and byte_counter != video_len:
		error_advice_exit('server did not send the expected ammount of data')

	video_file.close()
	cond_print('done.\n')
	cond_print('Video data saved to %s\n' % video_filename)

except (urllib2.URLError, ValueError, httplib.HTTPException, TypeError, socket.error):
	cond_print('failed.\n')
	error_advice_exit('unable to download video data')

except KeyboardInterrupt:
	sys.exit('\n')

# Finish
sys.exit()
