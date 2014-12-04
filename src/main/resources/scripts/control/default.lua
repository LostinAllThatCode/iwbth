-- Variables needed for do
dVelY 		= 0
jumpCount	= 0

-- Static final values to determine execution 
airdrag 	= .69
maxJumps 	= 2
maxVelY		= 8

function doBehaviour()
	physics 	= _self:getComponent(component("Physics"))
	anim		= _self:getComponent(component("Animatable"))
	
	vecVel = physics.body:getLinearVelocity()
	
	if physics.SENSOR_FEET then 
		jumpCount = 0
	else
		print(jumpCount)
	end
	
	if physics.SENSOR_FEET and isKeyDown(INPUT.KEY_JUMP) then 
		if dVelY == 0 then 
			dVelY = 4
		else
			dVelY = dVelY + 1
		end
		
		if dVelY >= maxVelY then 
			dVelY = maxVelY
		end
	end
	
	if physics.SENSOR_FEET and not isKeyDown(INPUT.KEY_JUMP) and dVelY > 0 and jumpCount == 0 then 
		vecVel.y = dVelY
		dVelY = 0
		jumpCount=jumpCount+1
		anim:setCurrentAnimation("JUMPING",true)
		--physics.SENSOR_FEET = false
	end	
	
	if not physics.SENSOR_FEET and isKeyDown(INPUT.KEY_JUMP) and jumpCount > 0 and jumpCount < maxJumps then
		if dVelY == 0 then 
			dVelY = 4
		else
			dVelY = dVelY + 1
		end
		if dVelY >= maxVelY then 
			dVelY = maxVelY
		end
	end
	
	if not physics.SENSOR_FEET and not isKeyDown(INPUT.KEY_JUMP) and dVelY > 0 and jumpCount > 0 and jumpCount < maxJumps then
		vecVel.y  = dVelY
		dVelY = 0
		jumpCount = jumpCount+1
		anim:setCurrentAnimation("JUMPING",true)
		--physics.SENSOR_FEET = false
	end
	
	if isKeyDown(INPUT.KEY_LEFT) and not isKeyDown(INPUT.KEY_RIGHT) then 
		vecVel.x = -5
	end
		
	if isKeyDown(INPUT.KEY_RIGHT) and not isKeyDown(INPUT.KEY_LEFT) then 
		vecVel.x = 5

	end

	if not isKeyDown(INPUT.KEY_LEFT) and not isKeyDown(INPUT.KEY_RIGHT) then 
		vecVel.x = 0
	end
		
	if not physics.SENSOR_FEET then 
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

function beginCollision(target)
	print("begin" .. target)
end

function endCollision(target)
	print("end" .. target)
end