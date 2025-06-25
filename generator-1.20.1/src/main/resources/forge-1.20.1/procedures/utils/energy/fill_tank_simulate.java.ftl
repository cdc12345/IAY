	private static int fillTankSimulate(LevelAccessor level, BlockPos pos, int amount,Direction direction) {
		AtomicInteger _retval = new AtomicInteger(0);
		BlockEntity _ent = level.getBlockEntity(pos);
		if (_ent != null)
			_ent.getCapability(ForgeCapabilities.FLUID_HANDLER, direction).ifPresent(capability ->
				_retval.set(capability.fill(new FluidStack(${generator.map(field$fluid, "fluids")}, amount), IFluidHandler.FluidAction.SIMULATE))
		);
		return _retval.get();
	}