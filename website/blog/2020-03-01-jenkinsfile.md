---
title: Tips for Jenkinsfiles
author: James Collier
authorURL: http://twitter.com/JRCCollier
authorImageURL: https://pbs.twimg.com/profile_images/1180918627383615488/keqBUzoJ_400x400.jpg
---

Jenkins is a very popular automation tool, used by many many companies to successfully orchestrate their CI/CD pipelines.
It's also the bane of many a developers existence and can quickly become a big time sink for teams. This post will look to
point out some of the common problems, suggesting solutions which work for me.

<!--truncate-->

The criticisms below are based purely on my own experiences in using Jenkins mainly as a tool to validate/publish and deploy
libraries and services. I am aware that some of the things I will complain about, are no doubt very useful features to support
specific use cases which other people may have.

## Use scripted pipelines

Firstly let's start with the `Jenkinsfile` [DSL]s. Jenkins supports 2 variants; declarative and scripted, declarative being
a simplified syntax, with scripted pipelines using Groovy expressions.
Declarative syntax was produced with aim of [reducing the need for developers to learn groovy](https://jenkins.io/doc/book/pipeline/syntax/#compare),
unfortunately developers are forced to learn the jenkins declarative syntax instead. For non-trivial pipelines where a
condition is required, the groovy syntax is usually much more familiar to what most developers might have seen before.

i.e.

```groovy
when { allOf { branch 'master'; environment name: 'DEPLOY_TO', value: 'production' } }
steps {
  // do something
}
```

vs

```groovy
if (env.BRANCH == 'master' && env.DEPLOY_TO == 'production') {
  // do something
}
```

The scripted pipeline also has the advantage of being much more explicit, so you don't need to waste time looking up
the documentation as to what caveats might exist for the [built-in condition](https://jenkins.io/doc/book/pipeline/syntax/#built-in-conditions).

## Credential naming scheme

If you were to see the username/password credential `artifactory-commerce-creds`, how would you know the name of the
environment variables that they should be set to.

Make it easy for your developers to know what the credentials are for, reuse the name of the environment variable.
So instead of having a single username/password credential, create two string credentials for each
environment variable `ARTIFACTORY_USERNAME` and `ARTIFACTORY_PASSWORD`. It's now obvious to anyone how these credentials should
be used. If you have multiple artifactory usernames/passwords for different contexts, just prefix something in front,
`MYTEAM_ARTIFACTORY_USERNAME`.

```groovy
withCredentials([
  string(credentialsId: 'ARTIFACTORY_USERNAME', variable: 'ARTIFACTORY_USERNAME'),
  string(credentialsId: 'ARTIFACTORY_PASSWORD', variable: 'ARTIFACTORY_PASSWORD')]) {
```

## Avoiding deep nesting

The [DSL] provided by Jenkins encourages deep nesting, in the [sequential stages](https://jenkins.io/doc/book/pipeline/syntax/#sequential-stages)
example, the pipeline is 9 levels deep. This can make it quite difficult to visually the correctness of your Jenkinsfile,
and Jenkins really doesn't assist much in validate syntactic correctness locally. You may choose to selectively avoid nesting for
multiple statements, e.g.

```groovy
node {
  ansiColor('xterm') {
  withCredentials([
    string(credentialsId: 'ARTIFACTORY_USERNAME', variable: 'ARTIFACTORY_USERNAME'),
    string(credentialsId: 'ARTIFACTORY_PASSWORD', variable: 'ARTIFACTORY_PASSWORD'),
    usernamePassword(credentialsId: 'GITHUB_TOKEN', usernameVariable: 'GITHUB_USER', passwordVariable: 'GITHUB_TOKEN')]) {
  
  checkout scm

  stage('Validate') {
    // validation logic here
  }

  } // end: withCredentials
  } // end: ansiColor
}
```

## Minimize the number of pipelines

Try and only produce additional pipelines when there's a clear benefit. Don't create
pipelines as a means to increase code reuse, prefer shared scripts or libraries. Most
of our services, have a main pipeline `Jenkinsfile` built on commit or tag push.
This pipelines serves to validate the build (tests/linting), then on the `master` branch
invoke a deploy pipeline. `jenkins/deploy-service.groovy` handles deploying the service,
with the setting concurrent builds disabled.

[DSL]: https://en.wikipedia.org/wiki/Domain-specific_language
