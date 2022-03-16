# hellozionative
An example with ZIO and scala native

#How to install

* Checkout the repository. If you are using WSL please ensure that repo is checked out to your home repository in WSL, such as `/home/myuser`
* Install libuv. For Ubuntu please run folloing command: ```sudo apt install libuv1```
* Create native binary by running ```sbt clean compile nativeLink```
* A binary will be in `target/scala-2.13` folder


