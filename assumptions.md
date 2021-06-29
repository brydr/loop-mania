* All random variables have uniform probability distributions - e.g. the additional damage inflicted by a vampire attack.
* Enemies have the following battle and support radii:
    * **Slugs** have a battle radius of 1 unit, support radius of 1 unit
    * **Zombies** have a battle radius of 2 units, support radius of 2 units
    * **Vampires** have a battle radius of 2 units, support radius of 3 units

[idea]: # (The size of each 'unit' scaled with the size of the world )
* Enemies will fight before *The Character* and allied soldiers. Allied soldiers strike, if any strike, then *The Character*, then any surviving enemies retaliate. The battle ends when either *The Character* or all enemies are killed.
* The player must press <kbd>Q</kbd> for *The Character* to consume a health potion.
* There is no maximum imposed on the amount of allied soldiers. 
<!-- TODO: 
Provide damage values/functions for:
* Unarmed
* Sword
* Stake (regular and Vampire)

How much HEALTH does a HEALTH POTION restore?

What is the speed, damage and health of an allied soldier? If the allied soldiers are WITH The Character, then they must be the same speed.

What is the speed, damage and health of The Character?



What behaviour does "provides defence" and "defends against enemy attacks" have for Armour and Shields/Helmets specifically?
* Damage reduction function?
* "Reduced by a scalar value" - does this mean 0.5x damage, for instance?
* How do these pieces interact? If its a scalar reduction, then does that mean a 20% reduction and 30% reduction simply becomes: 1 - (1 - 0.2)*(1 - 0.3) = 44% reduction

What are the SELLING and BUYING prices (with gold) of:
* Weapons: Sword, Stake, Staff
* Protection: Armour, Shield, Helmet, Health potion

What is the HEALTH and (base) DAMAGE and SPEED of:
* Zombies
* Vampires
* Slugs

What is the probability of a critical bite (additional attack) from a vampire?

What is the probability of a critical bite (turns allied solider into zombie) from a zombie?

What is the probability of a trance (turns enemy into allied soldier)?

HARD PART:

How are items/gold/cards/experience given out when an enemy is defeated?
How are items/gold/experience given out when a card is destroyed?
How are gold/cards/experience given out when an item is destroyed?


What is the min/max values of these uniformly-distributed random variables:
* Length of a trance - OR is this a function of the type of enemy converted
* Additional attack damage from vampire
* Gold/experience received (from card destroyed) - AND/OR is this a function of the type of card destroyed
* Gold/experience received (from item destroyed due to too many) - AND/OR is this a function of the type of item destroyed
* Gold/experience received (from defeating enemies) ? - AND/OR is this a function of the type of enemy defeated

One way: Each item/card gets its own independent dice-roll-like event/trial where, in theory, either no items/cards can be received or one of each (unlikely). The recieving gold/experience at all is treated the same. In this case:
* What are the PROBABILITIES of each occuring
* Is there a max on how many types we can recieve? e.g. End payout trials after 3 YESs
* Does this mean we cannot get multiples of the same item/card? Or can we add a random variable multiplier OR repeated trials.

-->

