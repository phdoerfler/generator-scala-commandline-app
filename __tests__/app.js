"use strict";
const path = require("path");
const assert = require("yeoman-assert");
const helpers = require("yeoman-test");

describe("generator-scala-commandline-app:app", () => {
  beforeAll(() => {
    return helpers.run(path.join(__dirname, "../generators/app")).withPrompts({
      scalaVersion: "2.13.6",
      version: "1.0-SNAPSHOT",
      organization: "com.example",
      organizationName: "acme",
      githubUser: "wayne",
      libs: ["scalapy"],
      scalapyVersion: "0.5.0",
      skipDoc: true
    });
  });

  it("creates files", () => {
    assert.file(["build.sbt"]);
  });
});
