
quartz {
    autoStartup = true
    jdbcStore = false
    waitForJobsToCompleteOnShutdown = true
}

environments {
    stage {
        quartz {
            autoStartup = true
        }
    }
    qa {
        quartz {
            autoStartup = true
        }
    }

    abccdev {
        quartz {
            autoStartup = true
        }
    }
    
    ec2dev {
        quartz {
            autoStartup = false
        }
    }
    
    ec2cdr {
        quartz {
            autoStartup = false
        }
    }
}
