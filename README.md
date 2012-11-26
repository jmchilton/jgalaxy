# Overview

JGalaxy is a Java/Swing graphical application for bulk downloading
files from [Galaxy][0]. The hope is that over time it will grow into a
full featured frontend for Galaxy, or at very least that features that
complement those provided by Galaxy currently will be added (such as
batch uploads).

[0]: http://galaxyproject.org

# Getting Started with JGalaxy

The simplest way to launch JGalaxy right now is using Java Web
Start. If you have a Java 6+ JVM installed, simply click [this
link][1] to launch JGalaxy.

The Java Webstart runtime will download the required files and you
will be prompted requesting permission to allow JGalaxy to run. Select
"Run" to continue.

![Image of Security Warning][started_1]

Once the application has launched, you will want to specify a new
Galaxy connection. To do this, click "New Connection" under the
"Connections" menu item.

![Image of New Connection][started_2]

To fill out the connection form, you will need to create a Galaxy API
key. To do this go to your Galaxy instance, and select API Keys from
the User menu.

![Image of API Keys Galaxy Menu Item][started_3]

If you have never generated an API key, you will need to generate one
using the "Generate a new key now" button.

![Image of Galaxy Account without API Key][started_4]

Highlight your API key, right click it, and copy it to your clipboard.

![Image of Galaxy Account with newly generated API Key][started_5]

Next return to JGalaxy, in the first field of the new connection
dialog select your Galaxy instance from the drop down menu or enter
its URL. In the second field, paste in your newly aquired API key.

![Image of New Connection Dialog][started_6]

Click the "Connect" button and you should be ready to go. The next
time you startup JGalaxy, an option for this connection should appear
in the "Connections" menu and the connection can be re-established by
just selecting the corresponding menu item.

![Image of Saved Connection][started_7]

[1]: http://artifactory.msi.umn.edu:8080/webstart/jgalaxy/jgalaxy.jnlp

[started_1]: https://github.com/jmchilton/jgalaxy/raw/master/docs/screenshots/started_1_warning.png
[started_4]: https://github.com/jmchilton/jgalaxy/raw/master/docs/screenshots/started_4_empty_key.png  
[started_7]: https://github.com/jmchilton/jgalaxy/raw/master/docs/screenshots/started_7_saved_key.png
[started_2]: https://github.com/jmchilton/jgalaxy/raw/master/docs/screenshots/started_2_new_connection.png  
[started_5]: https://github.com/jmchilton/jgalaxy/raw/master/docs/screenshots/started_5_new_key.png
[started_3]: https://github.com/jmchilton/jgalaxy/raw/master/docs/screenshots/started_3_api_key.png         
[started_6]: https://github.com/jmchilton/jgalaxy/raw/master/docs/screenshots/started_6_enter_key.png








