---
title: 12 Factor Tweaks
author: James Collier
authorURL: http://twitter.com/JRCCollier
authorImageURL: https://pbs.twimg.com/profile_images/1180918627383615488/keqBUzoJ_400x400.jpg
---

The [twelve-factor app methodology](https://12factor.net/) has been around for quite a while now.
If wikipedia were to be believed it was first presented by Adam Wiggins back in 2011. 

This post seeks to show some possible tweaks to this methodology based on the experience of running
and developing 12 factor applications from 2014.

## Beyond env vars

### Issues with env vars

The [config](https://12factor.net/config) section of the guide suggests that:

> The twelve-factor app stores config in environment variables (often shortened to env vars or env).
> Env vars are easy to change between deploys without changing any code; unlike config files, there is
> little chance of them being checked into the code repo accidentally; and unlike custom config files,
> or other config mechanisms such as Java System Properties, they are a language- and OS-agnostic standard.

This is fine, and has been successfully used for time, but we can do better. Environment
variables have some clear downsides. They aren't discoverable, if they aren't documented your likely going
to have to have a rummage around in the source. Secondly for build tools like [sbt](https://www.scala-sbt.org/)
where you predominantly run tasks from the sbt console, changing environment variables requires an sbt to be
restarted or settings to be modified from the console. Finally, if you mis-remember the name `DB_ENDPOINT` is actually
`DB_URL` you wont know of the naming mishap unless you check which url is being used.

A great alternative to environment variables is to use the CLI arguments instead.
So instead of doing something like:

```bash
ENVIRONMENT=dev && sbt run
^ctrl c
ENVIRONMENT=local && sbt run
```

You have the much nicer:

```bash
sbt run --env dev
sbt run --env local
```

This approach even has built in documentation:

```bash
sbt run --help
```

Oops I've used the wrong name:

```bash
$ sbt run --environment dev
> Unrecognized argument: --environment
```

### Using case-app
