'use strict';

const fs = require('fs');
const process = require('node:process');

let blocksToLookFor = ["redstoneelectronics:redstone_resistor", "redstoneelectronics:redstone_rotary_selector", 
"redstoneelectronics:redstone_rotary_distributer", "redstoneelectronics:redstone_motor"];

let blocksRaw = fs.readFileSync('src/generated/resources/reports/blocks.json');
let blockJson = JSON.parse(blocksRaw);

let blockKeys = Object.keys(blockJson).map(key => blockJson[key]);

if (!blocksToLookFor.every((blockName) => blockKeys.includes(blockName)))
{
    console.error("Error: not all blocks found");
    console.debug(blockKeys);
    console.debug(blocksToLookFor);
    process.exit(1);
}
let blockValues = blocksToLookFor.map((blockName) => blockKeys[blockName]);

if (!blockValues.every((block) => properties in block))
{
    console.error("Error: not all blocks have properties.");
    process.exit(2);
}

let blockProperties = blockValues.map((block) => block.properties);

let registriesraw = fs.readFileSync('src/generated/resources/reports/registries.json');

let registries = JSON.parse(registriesraw);

process.exit(0);