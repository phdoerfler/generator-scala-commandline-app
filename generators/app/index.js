"use strict";
const Generator = require("yeoman-generator");
const chalk = require("chalk");
const yosay = require("yosay");

module.exports = class extends Generator {
  async prompting() {
    this.log(
      yosay(
        `Welcome to the first-rate ${chalk.red(
          "Scala Commandline App"
        )} generator!`
      )
    );

    const promptsA = [
      {
        type: "list",
        name: "scalaVersion",
        message: `What do you want to use as ${chalk.red("scalaVersion")}?`,
        choices: ["2.13.6", "3.0.1"]
      },
      {
        type: "input",
        name: "version",
        message: `What do you want to use as ${chalk.red("version")}?`,
        default: "1.0-SNAPSHOT"
      },
      {
        type: "input",
        name: "organization",
        message: `What do you want to use as ${chalk.red("organization")}?`,
        default: "com.example",
        store: true
      },
      {
        type: "input",
        name: "organizationName",
        message: `What do you want to use as ${chalk.red("organizationName")}?`,
        default: this.user.git.name(),
        store: true
      },
      {
        type: "input",
        name: "githubUser",
        message: `What do you want to use as ${chalk.red("githubUser")}?`,
        default: "yourusername",
        store: true
      }
    ];

    this.propsA = await this.prompt(promptsA);

    const promptsB = [
      {
        type: "input",
        name: "homepage",
        message: `What do you want to use as ${chalk.red("homepage")}?`,
        default: `https://github.com/${this.propsA.githubUser}?`,
        store: true
      },
      {
        type: "input",
        name: "email",
        message: `What do you want to use as ${chalk.red("email")}?`,
        default: this.user.git.email(),
        store: true
      }
    ];

    this.props = { ...this.propsA, ...(await this.prompt(promptsB)) };

    this.composeWith(require.resolve("generator-license"), {
      name: this.props.fullName, // (optional) Owner's name
      email: this.props.email, // (optional) Owner's email
      website: this.props.homepage, // (optional) Owner's website
      defaultLicense: "BSD-3-Clause"
    });
  }

  writing() {
    this.fs.copyTpl(this.templatePath("."), this.destinationPath("."), {
      scalaVersion: this.props.scalaVersion,
      version: this.props.version,
      organization: this.props.organization,
      organizationName: this.props.organizationName,
      homepage: this.props.homepage
    });
  }

  install() {
    this.spawnCommandSync("sbt", ["update"]);
    const script = `target/universal/stage/bin/${this.determineAppname()}`;
    this.log(
      `Run ${chalk.red("sbt stage")} and then ${chalk.red(
        script
      )} to run your app outside of sbt.`
    );
  }
};
