package org.reallylastone.common.leadership.config;

import javax.sql.DataSource;

import org.reallylastone.leader_election_lib.AcquireLeadershipJob;
import org.reallylastone.leader_election_lib.LeadershipService;
import org.reallylastone.leader_election_lib.LeadershipServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "common.leadership", name = "enabled", havingValue = "true")
public class LeadershipAutoConfiguration {

    @Bean
    public LeadershipService leadershipService(DataSource dataSource,
            @Value("${common.leadership.app-id:1}") int appId,
            @Value("${common.leadership.timeout-ms:30000}") long timeoutMs) {
        return new LeadershipServiceImpl(dataSource, appId, timeoutMs);
    }

    @Bean
    public AcquireLeadershipJob acquireLeadershipJob(LeadershipService leadershipService) {
        return new AcquireLeadershipJob(leadershipService);
    }
}
