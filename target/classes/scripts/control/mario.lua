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
------------------------------------------------------------------------------------------

-- Preinitialized values to handle player input
isGrounded 	= false
dVelY		= 0f
dVelX		= 0f
jumpCount	= 0

-- Static final values to determine execution 
airdrag 	= 1f
friction	= 0.2f
maxJumps 	= 1
maxVelY		= 8f
maxVelX		= 6f

function doControls()
	physics = self:getComponent(getClazz("Physics"))
	controls = self:getComponent(getClazz("Controller")) 
	anim = self:getComponent(getClazz("Animatable")) 
	
	vecVel = physics.body:getLinearVelocity()
	
	if physics._feet then 
		jumpCount = 0
	end
	
	if physics._feet and (controls:isKeyDown(controls.KEY_JUMP) or controls:isButtonPressed(controls.CTRL_A)) then 
		dVelY = maxVelY
	end
	
	if physics._feet and not (controls:isKeyDown(controls.KEY_JUMP) or controls:isButtonPressed(controls.CTRL_A)) and dVelY > 0f and jumpCount == 0 then 
		vecVel.y = dVelY
		jumpCount = jumpCount + 1
		physics._feet = false
		dVelY = 0f
	end
	
	if (controls:isKeyDown(21) or controls:isPoVDown("west")) and not (controls:isKeyDown(22) or controls:isPoVDown("east")) then 
		if dVelX > 0f then dVelX = 0f end
		dVelX = dVelX - 0.1f
		if (dVelX <= -maxVelX) then dVelX = -maxVelX end
	end
		
	if (controls:isKeyDown(22) or controls:isPoVDown("east")) and not (controls:isKeyDown(21) or controls:isPoVDown("west")) then 
		if dVelX < 0f then dVelX = 0f end
		dVelX = dVelX + 0.1f
		if (dVelX >= maxVelX) then dVelX = maxVelX end
	end
	
	if not (controls:isKeyDown(21) or controls:isPoVDown("west")) and not (controls:isKeyDown(22) or controls:isPoVDown("east")) then dVelX = 0f end

	vecVel.x = dVelX

	if not physics._feet then 
		if vecVel.x < 0f then 
			anim:setCurrentAnimation("JUMPING",false,true,false) 
		elseif vecVel.x > 0f then 
			anim:setCurrentAnimation("JUMPING",false,false,false)
		end
	else 
		if vecVel.x < 0f then 
			anim:setCurrentAnimation("RUNNING",false,true,false) 
		elseif vecVel.x > 0f then 
			anim:setCurrentAnimation("RUNNING",false,false,false)
		else
			anim:setCurrentAnimation("IDLE")	
		end	
	end
	
	physics.body:setLinearVelocity(vecVel)
end

