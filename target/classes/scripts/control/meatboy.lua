boostSpeed	= .8
normalSpeed	= .5
turnMul 	= 4

-- Meat boy kind of movement implementation
function doBehaviour(velX, velY)
	dVelX = velX
	dVelY = velY

	
	if keyJump then 
		dVelY = maxVelY
		return dVelX,dVelY
	end
	
	if not walk() then dVelX = 0f end
	
	return dVelX,dVelY
end

function walkAccel()
	if keyShoot then return boostSpeed end
	return normalSpeed
end

function walk()
	sign = 0
	if keyRight then 
		sign = 1
	elseif keyLeft then 
		sign = -1 
	else 
		return false
	end
		
	curSign = math.sign(dVelX)
	v = walkAccel()
	
	if curSign ~= 0 and curSign ~= sign then v = v * turnMul end
		
	dVelX = dVelX + (v * sign)
	return true
end

function airControls()
	if dVelY > 0 and not keyJump then dVelY = 0 end
	walk()
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
