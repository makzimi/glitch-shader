# AGENTS.md

## Project

- `glitch-shader/` is the library, published to Maven Central as `io.github.makzimi:glitch-shader`.
- `sample/` is the demo app. It is not published.
- Build with JDK 17. Gradle 8.13 does not run on newer JDKs:
  `export JAVA_HOME=$(/usr/libexec/java_home -v 17)`

## Consumers

`upside-down-shader` (https://github.com/makzimi/upside-down-shader) uses this library in its sample.
After a release, bump `glitchShader` in that repo's `gradle/libs.versions.toml`.

## Code style

- Keep comments to a minimum. Only comment code that is hard to understand without it.
- Keep PR descriptions short: only what changed, in plain language.
- No "Co-Authored-By: Claude" or "Generated with Claude Code" in commits or PRs.

## Releasing a new version

Publishing uses the `com.vanniktech.maven.publish` plugin. The Maven Central token
(`mavenCentralUsername`, `mavenCentralPassword`) and the signing key (`signingInMemoryKey`,
`signingInMemoryKeyPassword`) are in `~/.gradle/gradle.properties` on the maintainer's machine.
Never commit them.

1. Merge the changes to `main` and pull:
   `git checkout main && git pull`
2. Bump the version in `coordinates(...)` in `glitch-shader/build.gradle.kts`.
   Update the version in `README.md` too. Commit and push.
3. Optional check. Publish locally and look at the POM in
   `~/.m2/repository/io/github/makzimi/glitch-shader/<version>/`:
   `./gradlew :glitch-shader:publishToMavenLocal`
4. Upload to Maven Central:
   `./gradlew :glitch-shader:publishToMavenCentral`
5. Open https://central.sonatype.com, go to View Deployments, wait for VALIDATED and press Publish.
   If it is FAILED, open the deployment to see the reason.
6. Tag the release:
   `git tag v<version> && git push origin v<version>`
7. Wait until the files appear at
   https://repo1.maven.org/maven2/io/github/makzimi/glitch-shader/<version>/
   This usually takes 10 to 30 minutes.

A published version can not be overwritten or deleted. To fix a bad release, publish a new version.
