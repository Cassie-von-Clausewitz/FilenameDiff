#!/usr/bin/env kscript

@file:DependsOn("com.andreapivetta.kolor:kolor:1.0.0")
@file:DependsOn("com.github.ajalt:clikt:2.6.0")

@file:Include("Cli.kt")
@file:Include("FilenameDiff.kt")

package filenamediff

import DependsOn
import Include

FilenameDiffCommand().main(args)
