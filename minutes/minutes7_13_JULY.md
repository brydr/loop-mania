# Minutes
Date: Tuesday, 13/07/21\
Time: 14:00-16:22\
Attendees: Jack, Dongzhu, Jaeff, Michael, Ben\
Author: Jack

## Comments:
Updated attack/takeDamage to cohesively work together\
shield negating vampire crit bite is in vampire.attack()\
weapon attack will return int and character attack will check for helmet\
trance will happen in enemy method convertToFriendly\
No longer doing state pattern for battles\
observer pattern implemented with AlliedSoldiers/Character\
health potion done working on frontend\
weapon strategy needs to be added\
item/character/enemies almost working together

## To Do:
For everyone:\
Testing before implementing\
Experiment with patterns that could be used in your section\
Remember to update LoopManiaWorld if it needs a function for your assigned epic\
Do all your work on a new gitlab branch named after your epic\
update uml when changes to design made

Dong: items, convertToFriendly, unarmed class\
Jaeff: fix up inheritance in enemies, work on runBattles with changes discussed\
Jack: update changes discussed in meeting, convertToEnemy, work on frontend health/xp/gold/allies\
Michael: goals as composite pattern, health potion front-end\
Ben: buildings classes, strategy pattern for difficulty

## Next Meeting:
15/07 @11am