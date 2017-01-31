package skirm

import scala.collection.mutable
import scala.util.Random
import elem._

//Worried about the number of created empts
class Hex(heigh:Int, ty:Int, here:Elem,){ //x:Int, y:Int){
  var located=here
  var typ=ty//new Environment(typ)
  var height=heigh
  //var locl=(x,y)
  def isEmpty= located.isEmpty

  def this(height:Int, typ:Int){
    this(height,typ,new Empt(),1,1)
  }

  def this()=this(0,0,new Empt(),1,1)

  def moveElem():Elem={
    var ret=located
    located=new Empt()
    ret
  }
  def placeElem(elem:Elem):Boolean={
    if(isEmpty){
      located=elem
      true
    }
    else{
      false
    }
  }
  def getLocl=located
  def setLocl(newLocl:Elem):Unit{
    located=newLocl
  }
}

//holds tatics game runner
/*
*TODO: finalize hexGenrator
*/
class World(size:Int, typ:Int)
{
  //size is x by x at least for testing
  val rand=new Random()
  val map=mapGenerator(hexGenerator(typ,size),size)//temporary map generator, will create a new one for a planned battle

  val (UL,UR,R,DR,DL,L)=((0,-1),(1,-1),(1,0),(0,1),(-1,1),(-1,0))

  def neighbor(locl:[(Int,Int)],direction:[(Int,Int)]):[(Int,Int)]={
    var ret=(0,0)
    ret._1=locl._1+direction._1
    ret._2=locl._2+direction._2
    ret
  }

  def mapGenerator(input:Stack[Hex],radius:Int):mutable.Map[Hex]={
    var ret:mutable.Map[(Int,Int),Hex]=Map()
    var col= 1
    var colmax=3
    var row= -1*radius
    var increasing=false
    for(i<- row to radius){
      if(increasing) colmax=colmax-1
      else col=col-1
      if(i==0) increasing=true
      for(j<-col to colmax){
        ret+=((row,col)->input.pop)
      }
    }

    }
    ret
  }

  def hexGenerator(typ:Int,radius:Int):Stack[Hex]=
  {
    //0 Flat
    //1 -1 - 2 slope up
    //2 -2 - 1 slope down
    //3 -2 - 2 hilly
    //4 -5 - 5 extreme
    //need to figure out general trends DONE
    var ma=0
    var mi=0
    var inc=0
    if(typ==1)
    {
      mi= -1
      ma=2
      inc= 1
    }
    else if(typ==2)
    {
      mi= -2
      ma=1
      inc= -1
    }
    else if(typ==3)
    {
      mi= -2
      ma=2
      inc= 0
    }
    else if(typ==4)
    {
      mi= -5
      ma=5
      inc= 0
    }
    val ret=Stack[Hex]()//Array.fill(size,size)(new Hex()) //empty
    var size = 1
    for(i<- 1 to radius){
      size = size + i * 6
    }
    for (i<-1 to size)
    {
      if(i%size==0)
      {
        mi+= inc
        ma+= inc
      }
      ret.push(new Hex(rand.nextInt(ma)+mi,0,new Empt()))
    }
    ret
  }
    //val grid=Array.fill(Array.fill(10,new Hex()))

  def move(from:(Int,Int),toward:(Int,Int)):Int=
  {
      if(map.contains(toward)&&(map(toward).getLocl.isEmpty()||map(toward).getLocl.canOverlap()){
        if(map(toward).getLocl.canOverlap()){
          map(from).getLocl.pickup(map(toward))
        }
        map(toward).setLocl(map(from).getLocl)
        map(from).setLocl(new Empt())
        return 1
      }
      else return 0
  }

  def fight(a:Actor,b:Actor):Int=
  {
    val rand=new Random()
    val attac=a
    val defen=b
    val roll=rand.nextFloat()
    var multMod=0.0//? need to figure out how to apply proper modifiers, thinking (mult*base)+add
    var addMod=0.0
    var damMultMod=0.0
    var damAddMod=0.0
    //Not drawn from here, i think
    //def getBaseHitChance(at:Actor,be:Actor):Double=at.getEmpathy(be)*(at.getHitAbil-be.getDodgeAbil)
    def getHitDamage(a:Actor,b:Actor)=a.getWeaponDam//-b.getArmorRes()//TODO Implement armor
    def getMods()=
    {
      //Done in Modifiers
      ???
      //multMod=s.getMultMods(attac)
      //addMod=s.getAddMods(attac)
      //damMultMod=s.getDamMult(attac)
      //damAddMod=s.getDamAdd(attac)
    }
    def calculateHit():Double=
    {
      var ret= multMod //* getBaseHitChance(attac, defen) +addMod
      ret
    }
    def doesHit():Boolean= if(calculateHit()>roll) true else false
    def calculateDamage():Double=
    {
      val dam=damMultMod*getHitDamage(attac,defen)+damAddMod
      if(doesHit())
      {
        if(getHitDamage(attac,defen)<0)return dam
        else if(dam<0)return 0.0
        else dam
      }
      else
      {
        0.0
      }
    }

  }

  def action(from:(Int,Int),toward:(Int,Int),num:Int):Int={
    //fight
    if(num==1)
    {
      return fight(map(from).getLocl,map(toward).getLocl)
    }
    //movement
    else if(num==2)
    {
      return move(from,toward)
    }
    //healing item
    else if(num==3)
    {
      return
    }
    //special ability
    else if(num==4)
    {

    }
    //wait
    else
    {

    }
  }
}

class Environment(numed:Int){
//On fire
//Water


}

/*
  TODO:Create inventory, act, magic, etc.
*/
/*
class Action(a:Actor,b:Actor){
  val fight=new Fight(a:Actor,b:Actor)
  def handleInput(in:Int):Unit={
    ???
  }
  def fightInfo():List[Double]={
    ???
  }
  def fightEnem():Unit={
  ???
  }
}
*/
/*
  A state is poorly defined
  right now it is being used as a relationship between two actors
  and as the definition of a World
  should establish it.
*/

class State(actor:Actor, target:Actor){
  //var force=0
  //var location=
  //val move=new Move(a:Actor)

  //val modif= new Modifiers()
}


/*
TODO:completely redo
*/
//class Combat(a:Actor,b:Actor){
  //for(i<-actors) ???
//}
