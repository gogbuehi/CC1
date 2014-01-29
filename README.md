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

I started writing features for the editor to try to automate programming PHP apps, but Netbeans released a very reliable PHP editor, so I abandoned the effort.
