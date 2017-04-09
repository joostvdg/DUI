#!/usr/bin/env bash
gradle build
gradle docker
gradle dockerRemoveContainer
gradle dockerRun
