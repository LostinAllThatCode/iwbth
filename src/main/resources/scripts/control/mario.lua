-- Preinitialized values to handle player input
isGrounded 	= false
dVelY		= 0
dVelX		= 0
jumpCount	= 0

-- Static final values to determine execution 
airdrag 	= 1
friction	= 0.2
maxJumps 	= 1
maxVelY		= 8
maxVelX		= 6

function doControls()
	physics = _self:getComponent(component("Physics"))
	controls = _self:getComponent(component("Controller")) 
	anim = _self:getComponent(component("Animatable")) 
	
	vecVel = physics.body:getLinearVelocity()
	
	if physics._feet then 
		jumpCount = 0
	end
	
	if physics._feet and (controls:isKeyDown(_input.KEY_JUMP) or controls:isButtonPressed(_input.CTRL_A)) then 
		dVelY = maxVelY
	end
	
	if physics._feet and not (controls:isKeyDown(_input.KEY_JUMP) or controls:isButtonPressed(_input.CTRL_A)) and dVelY > 0 and jumpCount == 0 then 
		vecVel.y = dVelY
		jumpCount = jumpCount + 1
		physics._feet = false
		dVelY = 0
	end
	
	if (controls:isKeyDown(_input.KEY_LEFT) or controls:isPoVDown("west")) and not (controls:isKeyDown(_input.KEY_RIGHT) or controls:isPoVDown("east")) then 
		if dVelX > 0 then dVelX = 0 end
		dVelX = dVelX - 0.1
		if (dVelX <= -maxVelX) then dVelX = -maxVelX end
	end
		
	if (controls:isKeyDown(_input.KEY_RIGHT) or controls:isPoVDown("east")) and not (controls:isKeyDown(_input.KEY_LEFT) or controls:isPoVDown("west")) then 
		if dVelX < 0 then dVelX = 0 end
		dVelX = dVelX + 0.1
		if (dVelX >= maxVelX) then dVelX = maxVelX end
	end
	
	if not (controls:isKeyDown(_input.KEY_LEFT) or controls:isPoVDown("west")) and not (controls:isKeyDown(_input.KEY_RIGHT) or controls:isPoVDown("east")) then dVelX = 0 end

	vecVel.x = dVelX

	if not physics._feet then 
		if vecVel.x < 0 then 
			anim:setCurrentAnimation("JUMPING",false,true,false) 
		elseif vecVel.x > 0 then 
			anim:setCurrentAnimation("JUMPING",false,false,false)
		end
	else 
		if vecVel.x < 0 then 
			anim:setCurrentAnimation("RUNNING",false,true,false) 
		elseif vecVel.x > 0 then 
			anim:setCurrentAnimation("RUNNING",false,false,false)
		else
			anim:setCurrentAnimation("IDLE")	
		end	
	end
	
	physics.body:setLinearVelocity(vecVel)
end

