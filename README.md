# Overview
CS 2340 (Fall 2016) Project: Clean Water Crowdsourcing
Team Members: David Arida, Osvaldo Armas, Owen Brahms, Alexandra Durso, Dacorvyn Young

## Features
Clean Slate is a JavaFX application that crowdsources clean water locations.

### Accounts
There are four types of accounts.
- User: the basic account. Users may submit water reports, which consist of a location (in latitude / longitude coordinates), the type of
water source, and a water condition. Users may also view water reports submitted by other users.
- Worker: designed for potential employees. Workers can, in addition to user actions, submit quality reports. These quality reports consist of a location, water condition, virus parts per million (ppm), and contaminant ppm.
- Manager: oversee workers. In addition to worker features, managers can view historical reports, which aggregate the average purity (either virus or contaminant ppm) of a water source in the last year. Managers can also view a record of all quality reports and delete a report, either water or quality.
- Admin: system managerial account. Admins can neither submit nor view reports. Admins may delete accounts, ban users from submitting reports, unblock users (from disabled accounts), and view logs.

### Security and Logging
Clean Slate uses salted hashes (for more information see https://github.com/defuse/password-hashing) to store passwords. Furthermore, an account is blocked after three incorrect login attempts within a 15 minute window. Once blocked, only an admin can block the account.

Clean Slate logs actions such as login attempts, account deletion, report deletion, account bans, and account unblocks.

### Other
Clean Slate offers basic multi-user support in the form of a remote database. For example, a user can create a report on one computer, then refresh the application on a second computer and be able to see the new report.

Clean Slate uses GoogleMaps to display report locations as map pins.

# Running the Program
Currently, the best way to run the program is through an IDE. Clone the repo locally, set each item in the /lib folder as a dependency, then run fxapp/Main.java.

# File Structure
Clean Slate uses a MVC architecture style, with each component getting a separate folder. Additional folders include /db (database code), /lib (external code and jars), /fxapp (contains the Main.java file used to run the program), and /tests (contains limited JUnits). Any subpackages are separated by feature.
