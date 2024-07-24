const json = require("./info.json");
const file = require("node:fs");
file.writeFileSync("./test.txt", JSON.stringify(json).replaceAll("\"", "\\" + "\""));

console.log(JSON.stringify(json).replaceAll("\"", "\\" + "\""));