lazy val protos = (project in file("protos"))
  .settings(
    name := "protos",
    libraryDependencies ++= Seq(
      "com.thesamet.test" % "test-protos" % "0.1" % "protobuf",
      "com.trueaccord.scalapb" %% "scalapb-runtime" % "0.5.47" % "protobuf",
      "com.devim" %% "proto-utils" % "0.1.0-SNAPSHOT" % "protobuf"
    ),

    // Dependencies marked with "protobuf" get extracted to target / protobuf_external
    // In addition to the JAR we care about, the protobuf_external directory
    // is going to contain protos from ScalaPB runtime and Google's standard
    // protos.  In order to avoid compiling them, we restrict what's compiled
    // to a subdirectory of protobuf_external
    PB.protoSources in Compile += target.value / "protobuf_external" / "com" / "thesamet",
    PB.protoSources in Compile += target.value / "protobuf_external" / "devim" / "protobuf",

    PB.targets in Compile := Seq(
      scalapb.gen() -> (sourceManaged in Compile).value
    )
  )

// Sub1 contains a proto file that imports a proto from test-protos.
lazy val sub1 = (project in file("sub1"))
  .settings(
    name := "sub1",

    // We add again the protos we need here so we can compile the protos
    // under `sub1/src/main/protobuf`. However, we do not generate code
    // for the dependencies this time since this is provided through the
    // dependency on protos project.
    libraryDependencies ++= Seq(
      "com.thesamet.test" % "test-protos" % "0.1" % "protobuf",
      "com.trueaccord.scalapb" %% "scalapb-runtime" % "0.5.47" % "protobuf",
      "com.devim" %% "proto-utils" % "0.1.0-SNAPSHOT" % "protobuf"
    ),

    PB.targets in Compile := Seq(
      scalapb.gen() -> (sourceManaged in Compile).value
    )
  )
  .dependsOn(protos)
