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
------------------------------------------------------------------------------------------


-- Variables needed for doControls
dVelY 		= 0f
jumpCount	= 0

-- Static final values to determine execution 
airdrag 	= .69f
maxJumps 	= 2
maxVelY		= 8f

function doControls()
	physics 	= self:getComponent(getClazz("Physics"))
	controls 	= self:getComponent(getClazz("Controller")) 
	anim		= self:getComponent(getClazz("Animatable"))
	
	vecVel = physics.body:getLinearVelocity()
	
	if physics._feet then 
		jumpCount = 0
	end
	
	if physics._feet and (controls:isKeyDown(controls.KEY_JUMP) or controls:isButtonPressed(controls.CTRL_A)) then 
		dVelY = dVelY + 1f
		if dVelY >= maxVelY then 
			dVelY = maxVelY
		end
	end
	
	if physics._feet and not (controls:isKeyDown(controls.KEY_JUMP) or controls:isButtonPressed(controls.CTRL_A)) and dVelY > 0f and jumpCount == 0 then 
		vecVel.y = dVelY
		dVelY = 0f
		jumpCount=jumpCount+1
		anim:setCurrentAnimation("JUMPING",true)
		physics._feet = false
	end	
	
	if not physics._feet and (controls:isKeyDown(controls.KEY_JUMP) or controls:isButtonPressed(controls.CTRL_A)) and jumpCount > 0 and jumpCount < maxJumps then
		dVelY = dVelY + 1f
		if dVelY >= maxVelY then 
			dVelY = maxVelY
		end
	end
	
	if not physics._feet and not (controls:isKeyDown(controls.KEY_JUMP) or controls:isButtonPressed(controls.CTRL_A)) and dVelY > 0f and jumpCount > 0 and jumpCount < maxJumps then
		vecVel.y  = dVelY
		dVelY = 0f
		jumpCount = jumpCount+1
		anim:setCurrentAnimation("JUMPING",true)
		physics._feet = false
	end
	
	if (controls:isKeyDown(21) or controls:isPoVDown("west")) and not (controls:isKeyDown(22) or controls:isPoVDown("east")) then 
		vecVel.x = -5f
	end
		
	if (controls:isKeyDown(22) or controls:isPoVDown("east")) and not (controls:isKeyDown(21) or controls:isPoVDown("west")) then 
		vecVel.x = 5f

	end

	if not (controls:isKeyDown(21) or controls:isPoVDown("west")) and not (controls:isKeyDown(22) or controls:isPoVDown("east")) then 
		vecVel.x = 0f
	end
		
	if not physics._feet then 
		vecVel.x = vecVel.x * airdrag
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