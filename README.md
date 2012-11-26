# Overview

JGalaxy is a Java/Swing graphical application for bulk downloading
files from [Galaxy][0]. The hope is that over time it will grow into a
full featured frontend for Galaxy, or at very least that features that
complement those provided by Galaxy currently will be added (such as
batch uploads).

# Running JGalaxy

Check out the [end-user documentation][1] for instructions on how to
run JGalaxy.

[0]: http://galaxyproject.org
[1]: http://github.com/jmchilton/jgalaxy/blob/master/docs/getting_started.md

# Building

JGalaxy can be built and tested with [Apache Maven][b1].

        % git clone git://github.com/jmchilton/jgalaxy.git
        % cd jgalaxy
        % mvn compile
        % mvn exec:java -Dexec.mainClass="com.github.jmchilton.jgalaxy.JGalaxy" -Dexec.classpathScope=runtime

JGalaxy is currently deployed as a Java Webstart application so ``mvn
package`` (required for redistribution), requires setting up a key to
sign your packages with - information on how to set this up and
configure it can be found in [pom.xml].

Once such a key is in place, ``mvn package`` produces files which can
be [deployed to a web server][b2]. You will need to update the
resulting ``.jnlp`` files codebase attribute to reflect the location
you are deploying JGalaxy to. 

One may also wish to deploy JGalaxy via a
[JnlpDownloadServlet][b3]. This approach is not what is currently
configured, but [this JGalaxy branch][b4] contains the changes
required to get this working.

[b1]: http://maven.apache.org/
[b2]: http://docs.oracle.com/javase/tutorial/deployment/webstart/deploying.html
[b3]: http://docs.oracle.com/javase/7/docs/technotes/guides/javaws/developersguide/downloadservletguide.html
[b4]: https://github.com/jmchilton/jgalaxy/tree/jnlpdownloadservlet

# License

The code is freely available under the [Apache License Version 2.0][l1].

[l1]: http://www.apache.org/licenses/LICENSE-2.0.html
