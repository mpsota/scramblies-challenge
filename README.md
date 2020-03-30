# Scramblies challenge
## Task 1
Complete the function (scramble str1 str2) that returns true if a portion of str1 characters can be rearranged to match str2, otherwise returns false

### Notes:
Only lower case letters will be used (a-z). No punctuation or digits will be included.
Performance needs to be considered

### Examples:
(scramble? "rekqodlw" "world") ==> true
(scramble? "cedewaraaossoqqyt" "codewars") ==> true
(scramble? "katas" "steak") ==> false

## Task 2
Create a web service that accepts two strings in a request and applies function scramble? from previous task to them.

## Task 3
Create a UI in ClojureScript with two inputs for strings and a scramble button. When the button is fired it should call the API from previous task and display a result.

## Notes
Please pay attention to tests, code readability and error cases.

## Usage

FIXME

## License

Copyright © 2020 FIXME

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
