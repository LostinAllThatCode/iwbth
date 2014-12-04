-- Preinitialized values to handle player input
isGrounded 	= false
dVelY		= 0
dVelX		= 0
jumpCount	= 0

-- Static final values to determine execution 
airdrag 	= .8
friction	= 0.2
maxJumps 	= 1
maxVelY		= 8
maxVelX		= 6

function doBehaviour()
	physics = _self:getComponent(component("Physics"))
	anim = _self:getComponent(component("Animatable")) 
	
	vecVel = physics.body:getLinearVelocity()
	
	if physics.SENSOR_FEET then 
		jumpCount = 0
	end
	
	if physics.SENSOR_FEET and isKeyDown(INPUT.KEY_JUMP) then 
		dVelY = maxVelY
	end
	
	if physics.SENSOR_FEET and not isKeyDown(INPUT.KEY_JUMP) and dVelY > 0 and jumpCount == 0 then 
		vecVel.y = dVelY
		jumpCount = jumpCount + 1
		physics.SENSOR_FEET = false
		dVelY = 0
	end
	
	if isKeyDown(INPUT.KEY_LEFT) and not isKeyDown(INPUT.KEY_RIGHT) and physics.SENSOR_FEET then 
		if dVelX > 0 then dVelX = 0 end
		dVelX = dVelX - 0.1
		if (dVelX <= -maxVelX) then dVelX = -maxVelX end
	end
		
	if isKeyDown(INPUT.KEY_RIGHT) and not isKeyDown(INPUT.KEY_LEFT) and physics.SENSOR_FEET then 
		if dVelX < 0 then dVelX = 0 end
		dVelX = dVelX + 0.1
		if (dVelX >= maxVelX) then dVelX = maxVelX end
	end
	
	if not isKeyDown(INPUT.KEY_LEFT) and not isKeyDown(INPUT.KEY_RIGHT) then 
		if physics.SENSOR_FEET then 
			dVelX = 0 
		else
			dVelX = dVelX * airdrag/6
		end
	end

	vecVel.x = dVelX * airdrag

	if not physics.SENSOR_FEET then 
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

function beginCollision(target)
	print("begin" .. target)
end

function endCollision(target)
	print("end" .. target)
end
