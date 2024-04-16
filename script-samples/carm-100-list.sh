#!/bin/bash

res=$(curl "$1")
pup '#margenZonaPrincipal > ul > li > a attr{href}' <<< "$res"
pup '#margenZonaPrincipal > ul > li > a text{}' <<< "$res"
