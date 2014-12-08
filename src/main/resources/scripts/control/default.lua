-- Variables needed for do
dVelY 		= 0
jumpCount	= 0

-- Static final values to determine execution 
airdrag 	= .6
maxJumps 	= 2
maxVelY		= 8
maxVelX		= 6
imp			= 1

function doBehaviour()
	
	
	physics 	= _self:getComponent(component("Physics"))
	anim		= _self:getComponent(component("Animatable"))
	
	position 	= physics:getBody():getPosition()
	vecVel 		= physics.body:getLinearVelocity()
	
	if physics.SENSOR_FEET then 
		jumpCount = 0
		drag = 1
	else
		drag = airdrag
	end
	
	if physics.SENSOR_FEET and isKeyDown(INPUT.KEY_JUMP) then 
		dVelY = dVelY + 1
		if dVelY >= maxVelY then 
			dVelY = maxVelY
		end
	end
	
	if physics.SENSOR_FEET and not isKeyDown(INPUT.KEY_JUMP) and dVelY > 0 and jumpCount == 0 then 
		vecVel.y = dVelY
		physics.body:setLinearVelocity(vecVel.x,vecVel.y);
		dVelY = 0
		jumpCount=jumpCount+1
		anim:setCurrentAnimation("JUMPING",true)
		physics.SENSOR_FEET = false
	end	
	
	if not physics.SENSOR_FEET and isKeyDown(INPUT.KEY_JUMP) and jumpCount > 0 and jumpCount < maxJumps then
		dVelY = dVelY + 1
		if dVelY >= maxVelY then 
			dVelY = maxVelY
		end
	end
	
	if not physics.SENSOR_FEET and not isKeyDown(INPUT.KEY_JUMP) and dVelY > 0 and jumpCount > 0 and jumpCount < maxJumps then
		vecVel.y = dVelY
		physics.body:setLinearVelocity(vecVel.x,vecVel.y);
		dVelY = 0
		jumpCount=jumpCount+1
		anim:setCurrentAnimation("JUMPING",true)
		physics.SENSOR_FEET = false
	end
	
	if isKeyDown(INPUT.KEY_LEFT) and not isKeyDown(INPUT.KEY_RIGHT) then 
		physics.body:applyLinearImpulse(-imp,0,position.x,position.y,true);
		if physics.SENSOR_FEET then anim:setCurrentAnimation("RUNNING") end
	end
		
	if isKeyDown(INPUT.KEY_RIGHT) and not isKeyDown(INPUT.KEY_LEFT) then 
		physics.body:applyLinearImpulse(imp,0,position.x,position.y,true);
		if physics.SENSOR_FEET then anim:setCurrentAnimation("RUNNING") end
	end
	
	vecVel = physics.body:getLinearVelocity()
	
	if not isKeyDown(INPUT.KEY_LEFT) and not isKeyDown(INPUT.KEY_RIGHT) then 
		if physics.SENSOR_FEET then 
			anim:setCurrentAnimation("IDLE")
		else
			physics.body:setLinearVelocity(0,vecVel.y)
		end
		
	end
	
	if (math.abs(vecVel.x) > maxVelX) then 
		if (math.sign(vecVel.x) == 1) then 
			anim:flip(false,false)
		else
			anim:flip(true,false)
		end
		vecVel.x = math.sign(vecVel.x) * maxVelX
		physics.body:setLinearVelocity(vecVel.x * drag,vecVel.y)
	end
	
end

function math.sign(x)
   if x<0 then
     return -1
   elseif x>0 then
     return 1
   else
     return 0
   end
end

function beginCollision(target)	
	print("begin" .. target)
end

function endCollision(target)
	print("end" .. target)
end