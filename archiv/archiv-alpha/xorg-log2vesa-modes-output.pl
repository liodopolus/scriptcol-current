#!/usr/bin/perl -w

my $resolution;
my $bpp;
my $vesamode;
my $curline;

printf STDOUT ("{| border=\"1\" cellpadding=\"2\"\n");
printf STDOUT ("!width=\"75\"|Resolution\n");
printf STDOUT ("!width=\"50\"|Bits per pixel\n");
printf STDOUT ("!width=\"50\"|Vesa mode\n");
printf STDOUT ("|-\n");
while ($curline=<>)
{
	if ( $curline =~ /Mode: ([0-9a-f]+) \(([0-9]+x[0-9]+)\).*$/s )
	{
	$vesamode=$1;
	$resolution=$2;
	}
	if ( $curline =~ /BitsPerPixel: ([0-9]+)$/s )
	{
	$bpp=$1;
	# printf STDOUT ("vesamode %s for resolution %s at %s bpp\n",$vesamode,$resolution,$bpp);
	printf STDOUT ("| %s || %s || %s\n",$resolution,$bpp,$vesamode);
	printf STDOUT ("|-\n");
	}
}
printf STDOUT ("|}\n");

