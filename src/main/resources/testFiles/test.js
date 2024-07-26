const json = require("./contact.json");
const file = require("node:fs");
file.writeFileSync("./test.txt", JSON.stringify(json).replaceAll("\"", "\\" + "\""));

console.log(JSON.stringify(json).replaceAll("\"", "\\" + "\""));