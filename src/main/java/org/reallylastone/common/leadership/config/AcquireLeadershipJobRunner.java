package org.reallylastone.common.leadership.config;

import org.reallylastone.leader_election_lib.AcquireLeadershipJob;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
@ConditionalOnProperty(prefix = "common.leadership", name = "enabled", havingValue = "true")
public class AcquireLeadershipJobRunner {

    private final AcquireLeadershipJob acquireLeadershipJob;

    @Scheduled(fixedDelay = 15_000, initialDelay = 5_000)
    public void run() {
        acquireLeadershipJob.run();
    }
}
