#!/bin/sh
# start django application with gunicorn
# start it from the top level directory where manage.py is 

# 1. method the simple way look at http://localhost:8000/
#gunicorn mysite.wsgi:application 

# 2. method advanced start, see gunicorn-localhost.conf for the upstream server port number
#gunicorn -w 2 -b 127.0.0.1:8012 mysite.wsgi:application 

# 3. method with gunicorn.conf.py
#http://docs.gunicorn.org/en/latest/deploy.html
#https://github.com/benoitc/gunicorn/blob/master/examples/gunicorn_rc

GUNICORN=$(which gunicorn)
ROOT=$(pwd)
#PID='/var/run/gunicorn.pid'

APP='mysite.wsgi:application'

#if [ -f $PID ]; then rm $PID; fi

#cd $ROOT
#exec $GUNICORN -c $ROOT/gunicorn.conf.py --pid=$PID $APP

# 1. start the app
exec $GUNICORN -c $ROOT/gunicorn.conf.py $APP

# 2. alternativly use django's runsverser
#$(python manage.py runserver 127.0.0.1:8012)


