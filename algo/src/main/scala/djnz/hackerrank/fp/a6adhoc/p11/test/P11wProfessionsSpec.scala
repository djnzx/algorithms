package djnz.hackerrank.fp.a6adhoc.p11.test

import cats.implicits._
import djnz.hackerrank.fp.a6adhoc.p11.P11ElementaryWatson.QueryCompiler

class P11wProfessionsSpec extends P11zSpecSupport {

  private val professionsProgram = List(
    "[ps: #a, #b, #c, #d].",
    "[has: #a, [ps: #a, #b, #c, #d]].",
    "[has: #b, [ps: #a, #b, #c, #d]].",
    "[has: #c, [ps: #a, #b, #c, #d]].",
    "[has: #d, [ps: #a, #b, #c, #d]]."
  )

  private val professionsQuery =
    "(<#Smith /= smith>, <#Baker /= baker>, <#Carpenter /= carpenter>, <#Tailor /= tailor>, <#Smiths-Son /= smith>, <#Bakers-Son /= baker>, <#Carpenters-Son /= carpenter>, <#Tailors-Son /= tailor>, <#Smith /= #Smiths-Son>, <#Baker /= #Bakers-Son>, <#Carpenter /= #Carpenters-Son>, <#Tailor /= #Tailors-Son>, <#Baker = #Carpenters-Son>, <#Smiths-Son = baker>, [has: smith, [ps: #Smith, #Baker, #Carpenter, #Tailor]], [has: baker, [ps: #Smith, #Baker, #Carpenter, #Tailor]], [has: carpenter, [ps: #Smith, #Baker, #Carpenter, #Tailor]], [has: tailor, [ps: #Smith, #Baker, #Carpenter, #Tailor]], [has: smith, [ps: #Smiths-Son, #Bakers-Son, #Carpenters-Son, #Tailors-Son]], [has: baker, [ps: #Smiths-Son, #Bakers-Son, #Carpenters-Son, #Tailors-Son]], [has: carpenter, [ps: #Smiths-Son, #Bakers-Son, #Carpenters-Son, #Tailors-Son]], [has: tailor, [ps: #Smiths-Son, #Bakers-Son, #Carpenters-Son, #Tailors-Son]])?"

  test("professions sample query") {
    val knowledge = buildKnowledge(professionsProgram)

    knowledge.query(QueryCompiler.queryToGoals(parseQuery(professionsQuery))) shouldBe
      Vector(
        Vector(
          "Baker".stv          -> "smith".stn,
          "Bakers-Son".stv     -> "tailor".stn,
          "Carpenter".stv      -> "tailor".stn,
          "Carpenters-Son".stv -> "smith".stn,
          "Smith".stv          -> "carpenter".stn,
          "Smiths-Son".stv     -> "baker".stn,
          "Tailor".stv         -> "baker".stn,
          "Tailors-Son".stv    -> "carpenter".stn
        ),
        Vector(
          "Baker".stv          -> "tailor".stn,
          "Bakers-Son".stv     -> "smith".stn,
          "Carpenter".stv      -> "smith".stn,
          "Carpenters-Son".stv -> "tailor".stn,
          "Smith".stv          -> "carpenter".stn,
          "Smiths-Son".stv     -> "baker".stn,
          "Tailor".stv         -> "baker".stn,
          "Tailors-Son".stv    -> "carpenter".stn
        ),
        Vector(
          "Baker".stv          -> "tailor".stn,
          "Bakers-Son".stv     -> "carpenter".stn,
          "Carpenter".stv      -> "smith".stn,
          "Carpenters-Son".stv -> "tailor".stn,
          "Smith".stv          -> "carpenter".stn,
          "Smiths-Son".stv     -> "baker".stn,
          "Tailor".stv         -> "baker".stn,
          "Tailors-Son".stv    -> "smith".stn
        ),
        Vector(
          "Baker".stv          -> "tailor".stn,
          "Bakers-Son".stv     -> "smith".stn,
          "Carpenter".stv      -> "baker".stn,
          "Carpenters-Son".stv -> "tailor".stn,
          "Smith".stv          -> "carpenter".stn,
          "Smiths-Son".stv     -> "baker".stn,
          "Tailor".stv         -> "smith".stn,
          "Tailors-Son".stv    -> "carpenter".stn
        )
      ).some
  }
}
