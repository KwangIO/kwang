#!/bin/sh

echo "Building Lwan"
cd lwan
mkdir build && cd build
cmake .. && make