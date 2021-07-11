# Minutes
Date: Tuesday, 11/07/21\
Time: 13:00-14:00\
Attendees: Jack, Dongzhu, Jaeff, Michael\
Absent: Ben\
Author: Jack

## Comments:
Character/AlliedSoldier/enemies/cards/gold semi-complete\
AlliedSoldier is now a MovingEntity to make attacking easier when passing a MovingEntity\
Battles work by character/allies attacking 1 enemy at a time, need to update assumptions\
We're looking at implementing a state pattern for the battle\
May incorporate state machine for battles to help with trance time\
Gold implemented, not sure if experience/cycles should be in character or loopmaniaworld\
add criticalbite assumptions for vampire, random num of future attacks\
Changed tasks, Jaeff doing cards, Michael doing consumables\
add assumption castle always at 0,0\
Forum reply said weapon couldn't be a strategy patern

## To Do:
For everyone:\
Testing and implementation for assigned epics\
Experiment with patterns that could be used in your section\
Remember to update LoopManiaWorld if it needs a function for your assigned epic\
Do all your work on a new gitlab branch named after your epic\
update uml when changes to design made

Dong: equippable items\
Jaeff: updating battles for vampire bite\
Dong/Jaeff: state pattern for battles\
Jack: observer pattern for list<AlliedSoldier>, update attack/takeDamage to use equippedItem methods\
Michael: goals, consumables(health potion)\
Ben: buildings classes, strategy pattern for difficulty

## Next Meeting:
Tuesday tute, @2pm 13 July