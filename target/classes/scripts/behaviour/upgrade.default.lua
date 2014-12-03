------------------------------------------------------------------------------------------
-- #####################################################################################--
-- PlayerController component script for org.gdesign.game engine
-- Documentation link : http://www.whatever.com
--
-- GLOBAL VARS AND FUNCTIONS | !DO NOT CHANGE!
	self = nil
	
	function init(e)
		self = e
		if self == nil then error("FATAL ERROR.") end
	end
	
	function getClazz(sName)
		clazz = luajava.bindClass("org.gdesign.platformer.components."..sName)
		return clazz
	end
-- #####################################################################################--

delta = 0

radius = 1
originX = 0
originY = 0
angle = 0
speed = .1f
started = false

function doBehaviour()
	physics = self:getComponent(getClazz("Physics"))
	
	if not started then 
		originX = physics.body:getPosition().x
		originY = physics.body:getPosition().y
		started = true
	end
	
	if started then
		angle = angle + speed
		if angle == 1 then angle = 0 end
		x = math.sin(angle) * radius
		y = math.cos(angle) * radius
		
		originX = originX + speed/100;
		
		physics.body:setTransform(originX + x, originY + y, 0);
	end
end


function handleCollision(target)
	self:destroy()
	
	phy = target:getComponent(getClazz("Physics"))
	pos = target:getComponent(getClazz("Position"))
	
	target:removeComponent(getClazz("Animatable"))
	newAnim = luajava.newInstance("org.gdesign.platformer.components.Animatable","textures/entity/player/mario.png")
	target:addComponent(newAnim)
	
	control = target:getComponent(getClazz("Controller"))
	control:setLuaScript("scripts/control/mario.lua",target)
	

end