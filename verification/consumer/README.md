# BitFlags publication consumer

This separate build declares only the staged producer root coordinate and Kotlin test.
The root, Android AAR, JVM JAR and macOS ARM64 KLIB coordinates resolve exclusively
from the absolute producerRepository property. Dependencies resolve from Central/Google;
no included project, source substitution or mavenLocal is used.

Stage KotlinMultiplatform, Android, JVM and MacosArm64 publications to
CentralPortalStagingRepository before running compileAndroidMain,
compileAndroidHostTest, testAndroidHostTest, jvmTest and macosArm64Test with the
same producerVersion. Set both Android SDK environment variables to the same SDK.

Four named common tests must execute on each of Android host, JVM and macOS ARM64.
Both consumer-owned public subclasses are intentionally visible to Native compilation:
the previous hidden producer superclass must be rejected rather than hidden in the caller.
Android raw compile/runtime classpaths are checked independently for duplicate classes.
This is not Android-device execution, Linux/Windows runtime coverage, or Swift validation.
