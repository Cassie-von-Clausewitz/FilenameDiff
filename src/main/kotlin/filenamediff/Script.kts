#!/usr/bin/env kotlin

@file:DependsOn("com.andreapivetta.kolor:kolor:1.0.0")
@file:DependsOn("com.github.ajalt:clikt:2.6.0")

@file:Include("Cli.kt")
@file:Include("FilenameDiff.kt")


import filenamediff.FilenameDiffCommand

FilenameDiffCommand().main(args)
