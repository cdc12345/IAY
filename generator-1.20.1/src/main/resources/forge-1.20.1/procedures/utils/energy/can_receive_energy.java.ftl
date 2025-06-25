private static boolean canReceiveEnergy(LevelAccessor level, BlockPos pos,Direction direction) {
		AtomicBoolean _retval = new AtomicBoolean(false);
		BlockEntity _ent = level.getBlockEntity(pos);
		if (_ent != null)
			_ent.getCapability(ForgeCapabilities.ENERGY, direction).ifPresent(capability ->
				_retval.set(capability.canReceive()));
		return _retval.get();
}