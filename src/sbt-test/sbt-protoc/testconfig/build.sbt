Project.inConfig(Test)(sbtprotoc.ProtocPlugin.protobufConfigSettings)

libraryDependencies += "com.google.protobuf" % "protobuf-java" % "3.1.0" % "protobuf"

PB.targets in Compile := Seq(PB.gens.java -> (sourceManaged in Compile).value)

PB.targets in Test := Seq(PB.gens.java -> (sourceManaged in Test).value)
