	private static int getFluidTankLevel(LevelAccessor level, BlockPos pos, int tank,Direction direction) {
		AtomicInteger _retval = new AtomicInteger(0);
		BlockEntity _ent = level.getBlockEntity(pos);
		if (_ent != null)
			_ent.getCapability(ForgeCapabilities.FLUID_HANDLER, direction).ifPresent(capability ->
				_retval.set(capability.getFluidInTank(tank).getAmount()));
		return _retval.get();
	}