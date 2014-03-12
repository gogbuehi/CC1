Update [2014-03-11]:
As I continue to work with this codebase, I am forced to confront the ignorance of my younger self. In particular, I am appalled at how many direct references to objects most of my classes require. I will be working to find a way to remove the vulnerability to memory leaks, so that this application meets my current standards for healthy object-oriented programming.
Some new features I'm integrating into the application:
 - A way to type non-ASCII characters using an ASCII keyboard
 -- This was inspired by my wife struggling to be able to type Amharic characters while she is doing reserach in Ethiopia. While
 - A feature to restore tabs automatically after closing the app
 -- This doesn't yet handle the situation where the application is suddenly halted (due to a crash or is killed by the OS).
 -- This will restore changes made to the documents
 -- This will restore the language map that is in use for the document, if it is available in the local application directory

History:
During my tenure at one of my first actual programming jobs, I found the IDE I was required to use to edit VBScript to be insufficient for a number of needs. As a result, I took it upon myself to brush up on my Java skills and write my own IDE to fulfill these inadequacies. From Summer of 2005 through to late 2006, I made updates to this app, adding features that helped automate or simplify my daily programming tasks.

While seemingly quaint now, the features I implemented for this application include:
 - A mechanism to maintain tab indentation when tabs are used
 - A search field to quickly navigate directories (works similar to Spotlight for Mac OS)
 - Indication of whether a file is ReadOnly (in the File List, below the directories)
  - This was to deal with the version control we used, which "locked" files by making them ReadOnly
 - Quick reformatting utilities for converting strings between VBScript carriage returns, HTML, and other common placeholder replacements that were required regularly
  - This is under the "Replace" menu
  - At this stage, these features are largely obsolete, since I don't need such peculiar string replacements or reformatting
 - A number of standard text editing features, such as:
  - Managing multiple tabs
  - Indicating files that have been edited, but not saved
  - Find and Replace
  - Undo and Redo
  - Keyboard Shortcuts (using Ctrl; this was developed on a Windows system)
  - Save As

This app came in handy, and I regularly developed using it. Had I more time, I considered trying to make it more accessible to other users, but IDEs quickly suprassed my featureset. Still, I enjoy that I can still compile the application and run it as a standalone app. I would have ported this to Android some time ago, but the TextViews don't offer a comparable API as JTextAreas. I think I can make up for the deficiency, should I attempt to port it again.

Another important note: I wrote this before I had learned how to maintain a version controlled repository for my source code. As a result, my code is riddled with commented code, in an attempt to preserve anything that might be useful later. Now that I'm actively developing in the code and it is now properly managed by a version control system, I'll be working to remove the needless comments, and (eventually) refactor the code to eliminate the interdependence between classes and improve the architecture of the application.

I started writing features for the editor to try to automate programming PHP apps, but Netbeans released a very reliable PHP editor, so I abandoned the effort.
