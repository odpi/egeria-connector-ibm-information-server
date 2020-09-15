#!/usr/bin/env bash

# SPDX-License-Identifier: Apache-2.0
# Copyright Contributors to the Egeria project.

releases=("v11502sp3" "v11502sp5" "v11502sp6" "v11700" "v11701" "v11701sp1" "v11702" "v11710" "v11710sp1" "v11710sp2")
parallelism=4
throttle=180

index=0
running=0
while [ $index -lt ${#releases[@]} ]; do
  next_release=${releases[$index]}
  echo "Starting CTS ($index) for release: $next_release ..."
  screen -S "cts_$next_release" -d -m ./runAndCollect.sh "$next_release"
  index=$((index+1))
  running=$((running+1))
  echo " ... throttling (delaying) for $throttle seconds"
  sleep $throttle
  while [ $running -ge $parallelism ]; do
    echo " ... maximum parallelism ($parallelism) reached, continuing to throttle"
    sleep $throttle
    running=$(screen -ls | grep -c "cts_" || true)
  done
done
