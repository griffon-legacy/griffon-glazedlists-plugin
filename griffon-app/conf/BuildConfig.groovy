griffon.project.dependency.resolution = {
    inherits("global")
    log "warn" 
    repositories {
        griffonHome()
        mavenCentral()
        mavenRepo 'http://repository.sonatype.org/content/groups/public'
    }
    dependencies {
        compile 'net.java.dev.glazedlists:glazedlists_java15:1.9.0'
    }
}

log4j = {
    appenders {
        console name: 'stdout', layout: pattern(conversionPattern: '%d [%t] %-5p %c - %m%n')
    }

    error 'org.codehaus.griffon',
            'org.springframework',
            'org.apache.karaf',
            'groovyx.net'
    warn  'griffon'
}
