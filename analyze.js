"use strict";

import fs from "fs";
import process from "node:process";

const blockDataToLookFor = {
  "redstoneelectronics:redstone_resistor": {
    facing: ["north", "south", "west", "east"],
    powered: ["true", "false"],
    resistance: ["1", "2", "3", "4"],
  },
  "redstoneelectronics:redstone_rotary_selector": {
    facing: ["north", "south", "west", "east"],
    powered: ["true", "false"],
    selector_orientation: ["left", "front", "right"],
  },
  "redstoneelectronics:redstone_rotary_distributer": {
    facing: ["north", "south", "west", "east"],
    powered: ["true", "false"],
    selector_orientation: ["left", "front", "right"],
  },
  "redstoneelectronics:redstone_motor": {
    currently_rotating: ["true", "false"],
    facing: ["north", "east", "south", "west", "up", "down"],
    powered: ["true", "false"],
  },
  "minecraft:stick": {
    facing: ["north", "east", "south", "west", "up", "down"],
    waterlogged: ["true", "false"],
  },
};

let blocksRaw = fs.readFileSync("src/generated/resources/reports/blocks.json");
let blockData = JSON.parse(blocksRaw);

const blockNames = Object.getOwnPropertyNames(blockData);
const blocksToLookFor = Object.getOwnPropertyNames(blockDataToLookFor);

for (let blockName of blocksToLookFor) {
  console.debug('Block Name: "%s".', blockName);
  if (!blockNames.includes(blockName)) {
    console.error("Error: block %s not in registry.", blockName);
    process.exit(1);
  }
  console.debug('Block "%s" found.', blockName);

  let block = blockData[blockName];
  let blockPropertiesToLookFor = blockDataToLookFor[blockName];

  if (!("properties" in block)) {
    console.error('Error: block "%s" has no properties.', blockName);
    process.exit(1);
  }

  let blockPropertyNames = Object.getOwnPropertyNames(block.properties);
  let blockPropertyNamesToFind = Object.getOwnPropertyNames(
    blockPropertiesToLookFor
  );
  for (let blockPropertyName of blockPropertyNamesToFind) {
    console.debug('Block Property Name to Find: "%s".', blockPropertyName);
    if (!blockPropertyNames.includes(blockPropertyName)) {
      console.error(
        'Error: block property "%s" not found in block "%s".',
        blockPropertyName,
        blockName
      );
      process.exit(1);
    }
    console.debug('Block Property "%s" found.', blockPropertyName);

    let blockPropertyValues = block.properties[blockPropertyName];
    let blockPropertyValuesToFind = blockPropertiesToLookFor[blockPropertyName];
    for (let blockPropertyValue of blockPropertyValuesToFind) {
      console.debug('Block Property Value to Find: "%s"', blockPropertyValue);
      if (!blockPropertyValues.includes(blockPropertyValue)) {
        console.error(
          'Error: block property value "%s" not found in block "%s".',
          blockPropertyValue,
          blockName
        );
        process.exit(1);
      }
      console.debug('Block Property Value "%s" found.', blockPropertyValue);
    }
  }
  console.debug(
    'Block Properties for block "%s" valid, checking states.',
    blockName
  );
}

let registriesraw = fs.readFileSync(
  "src/generated/resources/reports/registries.json"
);

let registries = JSON.parse(registriesraw);

process.exit(0);
