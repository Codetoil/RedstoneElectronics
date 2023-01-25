'use strict';

const fs = require('fs');
const process = require('node:process');

let blocksToLookFor = ["redstoneelectronics:redstone_resistor", "redstoneelectronics:redstone_rotary_selector", 
"redstoneelectronics:redstone_rotary_distributer", "redstoneelectronics:redstone_motor"];

let blocksRaw = fs.readFileSync('src/generated/resources/reports/blocks.json');
let blockJson = JSON.parse(blocksRaw);

let blockNames = Object.getOwnPropertyNames(blockJson).map(key => blockJson[key]);

for (let blockName in blocksToLookFor)
{
    if (!blockNames.includes(blockName))
    {
        console.error("Error: block not in registry: %s", blockName);
        process.exit(1);
    }
    let block = blockNames[blockName];

    if (!(properties in block))
    {
        console.error("Error: block %s has no properties.", blockName);
        process.exit(2);
    }
}

let registriesraw = fs.readFileSync('src/generated/resources/reports/registries.json');

let registries = JSON.parse(registriesraw);

process.exit(0);