#!/bin/sh

find . -name "target" -exec rm -rf '{}' \;
activator -jvm-debug 9999 run

