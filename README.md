# stress-testing-sample
While Running gatling I came accross this issue  ```
Error: Could not find or load main class Recorder```, to fix that make sure you are using proper scala version.
On intelliJ I am using ```2.12.7```.

Also make sure (if using intelliJ) to set the `Test Source Folders` to be pointing to `src/test/scala`.
On my local machine I have jdk 8. so that way gatling Engine can run properly
