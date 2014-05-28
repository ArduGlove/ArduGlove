#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
MKINTERFACE=$DIR/../../Java/MKInterface/build/install/MKInterface/bin/MKInterface

processing-java --sketch=$DIR  --output=/tmp/processing/ --present --force

$MKINTERFACE --mode mouse &
processing-java --sketch=$DIR  --output=/tmp/processing/ --present --force
kill $!

$MKINTERFACE --mode pointer &
processing-java --sketch=$DIR  --output=/tmp/processing/ --present --force
kill $!