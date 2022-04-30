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

    var prompts = [
      {
        type: "list",
        name: "scalaVersion",
        message: `What do you want to use as ${chalk.red("scalaVersion")}?`,
        choices: ["2.13.6", "3.1.0"]
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
      },
      {
        type: "confirm",
        name: "skipDoc",
        message: `Disable generation of scaladoc to speed up the build?`,
        default: "true",
        store: true
      },
      {
        type: "checkbox",
        name: "libs",
        message: `Which libraries would you like to use?`,
        default: ["specs2"],
        choices: [
          {
            name: "scalapy",
            value: "scalapy"
          },
          {
            name: "Scala Native",
            value: "scalanative"
          },
          {
            name: "specs2",
            value: "specs2"
          },
          {
            name: "akka",
            value: "akka"
          }
        ],
        store: true
      }
    ];
    this.props = await this.prompt(prompts);

    prompts = [
      {
        type: "input",
        name: "homepage",
        message: `What do you want to use as ${chalk.red("homepage")}?`,
        default: `https://github.com/${this.props.githubUser}?`,
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
    this.props = { ...this.props, ...(await this.prompt(prompts)) };

    if (this.props.libs.includes("scalapy")) {
      prompts = [
        {
          type: "input",
          name: "scalapyVersion",
          message: `Which version of ${chalk.red("scalapy")} do you want to use?`,
          default: "0.5.1",
          store: true
        }
      ];
      this.props = { ...this.props, ...(await this.prompt(prompts)) };
    }

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
      homepage: this.props.homepage,
      libs: this.props.libs,
      skipDoc: this.props.skipDoc,
      scalapyVersion: this.props.scalapyVersion
    });
  }

  install() {
    // Athis.spawnCommandSync("sbt", ["update"]);
    // Athis.spawnCommandSync("sbt", ["compile"]);
    this.spawnCommandSync("sbt", [";rootJVM/run --script a --address b;rootNative/run --script a --address b"]);
    const script = `target/universal/stage/bin/${this.determineAppname()}`;
    this.log(
      `Run ${chalk.red("sbt stage")} and then ${chalk.red(
        script
      )} to run your app outside of sbt.`
    );
  }
};
