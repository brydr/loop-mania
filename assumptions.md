# General
* The player has 100 HP of health.
* All random variables have uniform probability distributions - e.g. the additional damage inflicted by a vampire attack.

* Enemies will fight before *The Character* and allied soldiers. Allied soldiers strike, if any strike, then *The Character*, then any surviving enemies retaliate. The battle ends when either *The Character* or all enemies are killed.
* The player must press <kbd>Q</kbd> for *The Character* to consume a health potion.
* For each instance of the game where *The Character* moves from one tile to the next, there will be at most 1 battle with any enemy and any number of supporting enemies.


# Items
## Weapons
* Damage for each weapon is as follows:
    * An unarmed player's attack does 2 health point (HP) of damage.
    * A player with a **staff** deals 3 HP of damage, but has a small chance of inflicting a *trance*.
        * The trance has a 20% chance of occuring and has a random length between 3 seconds and 20 seconds.
    * A player with a **stake** deals 4 HP of damage to regular enemies (Slug, Zombie), but 16 HP of damage to Vampires.
    * A player with a **sword** does 8 HP of damage.

## Health Potions
* A health potion restores **30 HP** of health to *The Character*.
* A health potion has a 10% chance of dropping from defeating enemies.
* Each path tile in the game has a 1% chance of a health potion spawning on them every time *The Character* completes a full cycle.

## Rare Items
* The One Ring has a 3% chance of dropping from defeating enemies.
* Rare items can't be bought or sold in the shop

## Armour
* **Body armour** reduces enemy attack by 50%
* **Helmets** reduce enemy attack by 30%, but reduce player attack by 15%.
* **Shields** give each enemy attack a 30% chance of being ineffectual (e.g. 0 HP damage).
* Equipping both body armour and a helmet will have a compound effect - i.e. enemy attack will only deal `(1.00 - 0.30)*(1.00 - 0.50) = 35%` of their full damage.
* Any non-integer damage value will round to the nearest integer.

# Enemies and Allies
* There is no maximum imposed on the amount of allied soldiers.
* Both *The Character*, allied soldiers and enemies all have the same speed, moving at 1 tile per instance, except for Zombies which travel at half speed, moving at 0.5 tiles per instance meaning zombies will move one tile every two instances.
* Allied soldiers has 50 HP and don't have armour
* Enemies converted into allied soldier via trance can't use their special abilities
* When enemy converts to an ally (and vice versa), they keep their HP

## Enemies
* Enemies have the following battle and support radii:
    * **Slugs** have a battle radius of 1 unit, support radius of 1 unit
    * **Zombies** have a battle radius of 2 units, support radius of 2 units
    * **Vampires** have a battle radius of 2 units, support radius of 3 units
* A **Slug** has 30 HP of health, and its attack incurs 3 HP damage.
* A **Zombie** has 30 HP of health, and its attack incurs 6 HP of damage.
    * A crticial bite from a Zombie has a 10% chance of occuring.
* A **Vampire** has 60 HP of health, and its attack incurs 12 HP of damage.
    * A critical bite from a Vampire has a 20% chance of occuring and deals between 4 and 20 HP of additional damage.
    * Number of random Vampire attacks that will have the random additional damage will last for 1 to 3 attacks starting with the initial critical bite.

# Gold
* The following items have the following prices.
    * Sword: 150 gold (buy), 75 gold (sell)
    * Stake: 100 gold (buy), 50 gold (sell)
    * Staff: 200 gold (buy), 100 gold (sell)
    * Shield: 200 gold (buy), 100 gold (sell)
    * Armour: 200 gold (buy), 100 gold (sell)
    * Helmet: 120 gold (buy), 60 gold (sell)
    * Health Potions: 50 gold (buy), 20 gold (sell)
* Each path tile in the game has a 1% chance of gold spawning on them every time *The Character* completes a full cycle.
* Once an item is sold at the shop, the character can't buy the same item back

# Buildings
* A **trap** deals 30 HP of damage.
* A **tower** has a shooting radius of 4 units and deals 5 HP of damage.
* A **campfire** battle radius is 3 units.
* *The Character* regains 10 HP of health when passing through a **village**.
* A **Zombie Pit** has a spawn radius of 1 unit and zombies will spawn on any path tiles within that radius.
* A **Vampire Castle** has a spawn radius of 1 unit and vampires will spawn on any path tiles within that radius.

# Experience
* Defeating a vampire gives 200 XP.
* Defeating a zombie gives 40 XP.
* Defeating a slug gives 10 XP.

# Payouts: Defeated Enemies, Excess Items/Cards
* Items include the sword, stake, staff, armour, shield, helmet and health potion.
* When there are too many items **AND** cards, and an item/card is destroyed, the user will receive either experience or gold with a 50/50 chance.
* When there are **too many items**, a user will receive either a card, or experience, or gold - with a 1 in 3 chance of either.
* When there are **too many cards**, a user will receive either an item, or experience, or gold - with a 1 in 3 chance of either.
* When an **enemy is defeated**, a user will receive either gold, an item, or a card - with a 1 in 3 chance of either. They are guaranteed to receive experience.
* Gold Payout:
    * A gold payout will be a random value between 10 and 100.
* Item Payout:
    * Each possible item has an equal probability of being received (1 in 7).
* Card Payout:
    * Each possible card has an equal probability of being received (1 in 7).
* Experience Payout:
    * An experience payout will be a random value between 10 and 30.
<!-- TODO:

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
