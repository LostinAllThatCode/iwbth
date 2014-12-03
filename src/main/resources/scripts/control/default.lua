-- Variables needed for doControls
dVelY 		= 0
jumpCount	= 0

-- Static final values to determine execution 
airdrag 	= .69
maxJumps 	= 2
maxVelY		= 8

function doControls()

	physics 	= _self:getComponent(component("Physics"))
	controls 	= _self:getComponent(component("Controller")) 
	anim		= _self:getComponent(component("Animatable"))
	
	vecVel = physics.body:getLinearVelocity()
	
	if physics._feet then 
		jumpCount = 0
	end
	
	if physics._feet and (controls:isKeyDown(_input.KEY_JUMP) or controls:isButtonPressed(_input.CTRL_A)) then 
		if dVelY == 0 then 
			dVelY = 4
		else
			dVelY = dVelY + 1
		end
		
		if dVelY >= maxVelY then 
			dVelY = maxVelY
		end
	end
	
	if physics._feet and not (controls:isKeyDown(_input.KEY_JUMP) or controls:isButtonPressed(_input.CTRL_A)) and dVelY > 0 and jumpCount == 0 then 
		vecVel.y = dVelY
		--print(dVelY)
		dVelY = 0
		jumpCount=jumpCount+1
		anim:setCurrentAnimation("JUMPING",true)
		physics._feet = false
		
	end	
	
	if not physics._feet and (controls:isKeyDown(_input.KEY_JUMP) or controls:isButtonPressed(_input.CTRL_A)) and jumpCount > 0 and jumpCount < maxJumps then
		if dVelY == 0 then 
			dVelY = 4
		else
			dVelY = dVelY + 1
		end
		if dVelY >= maxVelY then 
			dVelY = maxVelY
		end
	end
	
	if not physics._feet and not (controls:isKeyDown(_input.KEY_JUMP) or controls:isButtonPressed(_input.CTRL_A)) and dVelY > 0 and jumpCount > 0 and jumpCount < maxJumps then
		vecVel.y  = dVelY
		dVelY = 0
		jumpCount = jumpCount+1
		anim:setCurrentAnimation("JUMPING",true)
		physics._feet = false
	end
	
	if (controls:isKeyDown(21) or controls:isPoVDown("west")) and not (controls:isKeyDown(22) or controls:isPoVDown("east")) then 
		vecVel.x = -5
	end
		
	if (controls:isKeyDown(22) or controls:isPoVDown("east")) and not (controls:isKeyDown(21) or controls:isPoVDown("west")) then 
		vecVel.x = 5

	end

	if not (controls:isKeyDown(21) or controls:isPoVDown("west")) and not (controls:isKeyDown(22) or controls:isPoVDown("east")) then 
		vecVel.x = 0
	end
		
	if not physics._feet then 
		vecVel.x = vecVel.x * airdrag
		if vecVel.x < 0 then 
			anim:setCurrentAnimation("JUMPING",false,true,false) 
		elseif vecVel.x > 0 then 
			anim:setCurrentAnimation("JUMPING",false,false,false)
		end
	else 
		if vecVel.x < 0 then 
			anim:setCurrentAnimation("RUNNING",false,true,false) 
		elseif vecVel.x > 0.0 then 
			anim:setCurrentAnimation("RUNNING",false,false,false)
		else
			anim:setCurrentAnimation("IDLE")	
		end	
	end

	physics.body:setLinearVelocity(vecVel)
end