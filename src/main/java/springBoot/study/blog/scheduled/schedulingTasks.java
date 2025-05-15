package springBoot.study.blog.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import springBoot.study.blog.repository.PostRepository;
import springBoot.study.blog.repository.UserRepository;
@Component
@Slf4j
public class schedulingTasks {
    private static final Logger log = LoggerFactory.getLogger(schedulingTasks.class);
    @Autowired
    private UserRepository userRepository ;
    @Scheduled(cron = "0 * * * * *")
    public void countUsers(){
        long count = userRepository.count() ;
        log.info("there are {} users in database .", count);

    }
}
